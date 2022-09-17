import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://cognito-idp.us-east-1.amazonaws.com/us-east-1_Y5EI1inok',
            redirectUrl: window.location.origin,
            postLogoutRedirectUri: window.location.origin,
            clientId: '2nordg6elfrplj1tt4767n3110',
            scope: 'openid profile', // 'openid profile ' + your scopes
            responseType: 'code',
            silentRenew: true,
            silentRenewUrl: window.location.origin + '/silent-renew.html',
            renewTimeBeforeTokenExpiresInSeconds: 10,
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
