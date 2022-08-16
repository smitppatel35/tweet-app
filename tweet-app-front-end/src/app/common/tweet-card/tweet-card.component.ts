import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {faHeart} from "@fortawesome/free-solid-svg-icons";
import { faReply } from '@fortawesome/free-solid-svg-icons';
import {Tweet} from "../../models/Tweet";
import {TweetService} from "../../services/tweet/tweet.service";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {EditTweetComponent} from "../popups/edit-tweet/edit-tweet.component";
import { faEdit } from '@fortawesome/free-regular-svg-icons';
import {ReplyTweetComponent} from "../popups/reply-tweet/reply-tweet.component";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-tweet-card',
  templateUrl: './tweet-card.component.html',
  styleUrls: ['./tweet-card.component.css']
})
export class TweetCardComponent implements OnInit {
  @Output() refreshEvent = new EventEmitter<string>();
  @Input("tweet") tweet: Tweet = new Tweet("null", "null", "null", "null", "null", "null", "null", [], "null");

  faLike = faHeart;
  faReply = faReply;
  faEdit = faEdit;

  username = this.cookieService.get("username");

  constructor(private _tweetService: TweetService, private modalService: NgbModal, private cookieService: CookieService) { }

  ngOnInit(): void {
  }

  callParentToRefresh() {
    this.refreshEvent.emit('refresh');
  }

  openEditModal(){
    const modelRef = this.modalService.open(EditTweetComponent);
    modelRef.componentInstance.tweet = this.tweet.tweet;
    modelRef.componentInstance.tweetId = this.tweet.tweetId;
  }

  openReplyModal() {
    const modelRef = this.modalService.open(ReplyTweetComponent);
    modelRef.componentInstance.replyList = this.tweet.reply;
    modelRef.componentInstance.tweetId = this.tweet.tweetId;

    modelRef.result.then(res => {
      this.refreshEvent.emit();
    }).catch((res) => {
      console.log(res);
    });

  }

  like(id: string){
    this._tweetService.likeTweet(id);
    this.callParentToRefresh();
  }

}
