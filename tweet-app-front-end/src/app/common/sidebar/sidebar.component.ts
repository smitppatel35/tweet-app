import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {UserService} from "../../services/user/user.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {


  avatar: string;
  name: string;
  username: string;

  constructor(public oidcSecurityService: OidcSecurityService) { }

  ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe(({isAuthenticated, userData }) => {
      this.avatar = `https://tweet-app-avatars.s3.us-east-1.amazonaws.com/${userData['username']}.svg`
      this.name = userData['name'];
      this.username = userData['email'].split("@")[0];
    });
  }

}
