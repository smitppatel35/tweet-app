import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {UserService} from "../../services/user/user.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  user: User = new User("", "","", "", "", "", "");

  constructor(private router: Router, private userService: UserService, private cookieService: CookieService) { }

  ngOnInit(): void {
    let username = this.cookieService.get("username");

    if (username && username !== '') {
      this.userService.profile(username).subscribe(data => {
        this.user = data[0];
      });
    } else {
      this.router.navigate(['/']);
    }

  }

}
