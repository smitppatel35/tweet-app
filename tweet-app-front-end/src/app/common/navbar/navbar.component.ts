import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isAuthenticated: boolean = this.cookieService.get("username") !== '';

  constructor(private userService: UserService, private cookieService: CookieService, private router: Router) { }

  ngOnInit(): void {
  }

  logout(){
    this.userService.logout();
  }
}
