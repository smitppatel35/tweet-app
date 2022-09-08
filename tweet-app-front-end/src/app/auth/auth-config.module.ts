import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://cognito-idp.ap-south-1.amazonaws.com/ap-south-1_QWVbTtcmV',
            redirectUrl: window.location.origin,
            postLogoutRedirectUri: window.location.origin,
            clientId: '6mkppu1sqo64ce0uaf21lqsi90',
            scope: 'openid profile', // 'openid profile ' + your scopes
            responseType: 'code',
            silentRenew: false,
            silentRenewUrl: window.location.origin + '/silent-renew.html',
            renewTimeBeforeTokenExpiresInSeconds: 10,
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
