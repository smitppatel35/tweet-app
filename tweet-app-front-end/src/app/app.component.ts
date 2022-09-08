import { Component, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { CheckAuthService } from 'angular-auth-oidc-client/lib/auth-state/check-auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tweet-app-front-end';

  // constructor(public oidcSecurityService: OidcSecurityService) {
    
  // }
  // ngOnInit(): void {
  //   this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated, userData, accessToken, idToken }) => {
  //     console.log("IsAuthenticated: " + isAuthenticated);
  //     console.log("User Data " + JSON.stringify(userData));

  //     if(!isAuthenticated) {
  //       this.oidcSecurityService.authorize();
  //     }
  //   });
  // }
}
