import {Injectable} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../../models/User";
import {CookieService} from "ngx-cookie-service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ApiPaths} from "../ApiPaths";
import {Tweet} from "../../models/Tweet";

interface Login {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_URL = environment.API_URL;

  constructor(private router: Router, private cookieService: CookieService, private http: HttpClient) {
  }

  getAllUserProfiles(){
    return this.http.get(this.API_URL + ApiPaths.TWEET_BASE_PATH + "users/all").pipe(
      map((data: User[]) => {
        return data;
      }), catchError(err => {
        return throwError('Api Call Failed. cause: ' + err);
      }));
  }

  login(user: Login) {
    if (user.email !== '' && user.password !== '') {
      this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + "login", user).subscribe((res) => {
        this.cookieService.set('username', res['username'], 0.25, "/", "localhost", true);
        this.router.navigate(['/']);
      }, error => {
        console.log(error);
      });
    }
  }

  register(user: User){
    this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + "register", user).subscribe((res) => {
      this.router.navigate(['/']);
    }, error => {
      console.log(error);
    });
  }

  profile(username: string): Observable<User[]> {
    return this.http.get(this.API_URL + ApiPaths.TWEET_BASE_PATH + "search/" + username).pipe(
      map((data: User[]) => {
        return data;
      }), catchError(err => {
        return throwError('Api Call Failed. cause: ' + err);
      }));
  }

  forgotPassword(username: string, newPassword: string){
    this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + username + "/forgot",
      {
        "newPassword": newPassword
      }
      ).subscribe(data => {
        this.router.navigate(['/auth/login']);
    });
  }

  logout() {
    this.cookieService.delete("username");
    this.router.navigate(['/auth/login']);
  }
}
