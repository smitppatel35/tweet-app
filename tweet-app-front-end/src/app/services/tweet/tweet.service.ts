import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiPaths} from "../ApiPaths";
import {Tweet} from "../../models/Tweet";
import {catchError, map, Observable, Subject, throwError} from "rxjs";
import {environment} from "../../../environments/environment";
import {CookieService} from "ngx-cookie-service";
import { OidcSecurityService } from 'angular-auth-oidc-client';

class User {
  username: string;
  name: string;
  gender: string;
  email: string;

  constructor(
    username: string,
    name: string,
    gender: string,
    email: string) {
    this.username = username;
    this.name = name;
    this.gender = gender;
    this.email = email;
  }
}

class TweetRequest {

  tweet: string;
  username: string;
  name: string;
  gender: string;
  email: string;
  
  constructor(user: User, tweet: string) {
    this.tweet = tweet;
    this.username = user.username;
    this.name = user.name;
    this.gender = user.gender;
    this.email = user.email;
  }
}

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  private API_URL = environment.API_URL;
  user: User;

  constructor(private http: HttpClient, private cookieService: CookieService, public oidcSecurityService: OidcSecurityService) { 
    oidcSecurityService.checkAuth().subscribe(({isAuthenticated, userData }) => {
      this.user = new User(userData['username'], userData['name'], userData['gender'], userData['email']);
    });
  }

  // tweet CRUD

  save(message: string): Observable<any>{
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + "add", 
    new TweetRequest(this.user, message), {
      headers: headers
    });
  }

  updateTweet(id: string, message: string): Observable<any> {
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    let username = this.cookieService.get("username");
    return this.http.put(this.API_URL + ApiPaths.TWEET_BASE_PATH + this.user['username'] + "/update/" + id, new TweetRequest(this.user, message), {
      headers: headers
    });
  }

  getAllTweet(): Observable<Tweet[]>{
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });
    
    // @ts-ignore
    return this.http.get(this.API_URL + ApiPaths.API_GET_ALL_TWEETS, {
      headers: headers
    })
      .pipe(
        map((data: Tweet[]) => {
          return data;
        }), catchError(err => {
          return throwError('Api Call Failed. cause: ' + err);
        })
    );
  }

  likeTweet(id: string): Observable<any> {
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.put(this.API_URL + ApiPaths.TWEET_BASE_PATH + this.user['username'] + "/like/"+id, null, {
      headers: headers
    });
  }

  replyTweet(id: string, message: string): Observable<any>{
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.post(this.API_URL + ApiPaths.TWEET_BASE_PATH + this.user['username'] + "/reply/" + id, new TweetRequest(this.user, message), {
      headers: headers
    });
  }

  delete(id: string): Observable<any> {
    var accessToken;

    this.oidcSecurityService.getAccessToken().subscribe((token) => {
      accessToken = token;
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.delete(this.API_URL + ApiPaths.TWEET_BASE_PATH + this.user['username'] + "/delete/"+id, {
      headers: headers
    });
  }
}
