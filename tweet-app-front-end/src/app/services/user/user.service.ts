import {Injectable} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../../models/User";
import {CookieService} from "ngx-cookie-service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ApiPaths} from "../ApiPaths";
import {Tweet} from "../../models/Tweet";
import { OidcSecurityService } from 'angular-auth-oidc-client';

interface Login {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_URL = environment.API_URL;

  constructor(
    private router: Router, 
    private cookieService: CookieService, 
    private http: HttpClient,
    public oidcSecurityService: OidcSecurityService) {
  }

  getAllUserProfiles(){
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });
    
    return this.http.get(this.API_URL + ApiPaths.TWEET_BASE_PATH + "users/all", {
      headers: headers
    }).pipe(
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
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.get(this.API_URL + ApiPaths.TWEET_BASE_PATH + "search/" + username, {
      headers: headers
    }).pipe(
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

  // logout() {
  //   console.log("Logging off");
    
  //   this.cookieService.delete("username");
  //   this.oidcSecurityService.logoffAndRevokeTokens();
  //   this.router.navigate(['/home']);
  // }
}
