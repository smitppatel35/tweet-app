import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {map, Observable, take} from 'rxjs';
import {CookieService} from "ngx-cookie-service";
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private cookieService: CookieService, private router: Router, public oidcSecurityService: OidcSecurityService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    var auth = false;

    this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated, userData}) => {
      console.log(isAuthenticated);

      if(!isAuthenticated){
        // this.oidcSecurityService.authorize();
        this.router.navigate(['/auth/login'])
      } else {
        this.cookieService.set('username', userData['username'], 0.25, "/", "localhost", true);
        auth = true;
      }
     
    });

    return auth;
  }

}
