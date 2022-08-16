import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {TweetService} from "../../../services/tweet/tweet.service";
import {Reply} from "../../../models/Reply";

@Component({
  selector: 'app-reply-tweet',
  templateUrl: './reply-tweet.component.html',
  styleUrls: ['./reply-tweet.component.css']
})
export class ReplyTweetComponent implements OnInit {

  @Output() refreshEvent = new EventEmitter<string>();
  replyList: Reply[] = [];

  tweetReply = '';
  tweetId = "null";

  constructor(public activeModal: NgbActiveModal, private _tweetService: TweetService) { }

  ngOnInit(): void {
  }

  submit() {
    this._tweetService.replyTweet(this.tweetId, this.tweetReply);
    this.tweetReply = '';

    this.activeModal.close("close click");
  }
}
