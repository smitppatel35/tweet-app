import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isAuthenticated = false;

  constructor(
    private userService: UserService,
    private cookieService: CookieService, 
    private router: Router,
    public oidcSecurityService: OidcSecurityService) { }

  ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe(({isAuthenticated}) => {
      this.isAuthenticated = isAuthenticated;
    })
  }

  logout(){
    this.cookieService.delete("username");
    this.oidcSecurityService.logoff();
  }
}
