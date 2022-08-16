import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import {HttpClientModule} from "@angular/common/http";
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { NavbarComponent } from './common/navbar/navbar.component';
import { TweetCardComponent } from './common/tweet-card/tweet-card.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditTweetComponent } from './common/popups/edit-tweet/edit-tweet.component';
import { ReplyTweetComponent } from './common/popups/reply-tweet/reply-tweet.component';
import {CookieService} from "ngx-cookie-service";
import { LoginLayoutComponent } from './common/layout/login-layout/login-layout.component';
import { HomeLayoutComponent } from './common/layout/home-layout/home-layout.component';
import { TweetsComponent } from './pages/tweets/tweets.component';
import { UsersComponent } from './pages/users/users.component';
import { SearchComponent } from './pages/search/search.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    SidebarComponent,
    NavbarComponent,
    TweetCardComponent,
    EditTweetComponent,
    ReplyTweetComponent,
    LoginLayoutComponent,
    HomeLayoutComponent,
    TweetsComponent,
    UsersComponent,
    SearchComponent,
    ForgotPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FontAwesomeModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
