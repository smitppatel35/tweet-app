import {Reply} from "./Reply";

export class Tweet {
  tweetId: string;
  username: string;
  firstName: string;
  lastName: string;
  tweet: string;
  avatar: string;
  likes: string;
  reply: Reply[];
  timestamp: string;

  constructor(tweetId: string, username: string, firstName: string, lastName: string, tweet: string, avatar: string, likes: string, reply: Reply[], timestamp: string) {
    this.tweetId = tweetId;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.tweet = tweet;
    this.avatar = avatar;
    this.likes = likes;
    this.reply = reply;
    this.timestamp = timestamp;
  }
}
