import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpClient, HttpClientModule, provideHttpClient } from '@angular/common/http';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
  provideHttpClient(),
  importProvidersFrom(HttpClientModule),
  importProvidersFrom(TranslateModule.forRoot({
    loader: {
      provide: TranslateLoader,
      useFactory: HttpCLoaderFactory,
      deps: [HttpClient]
    }
  })), provideAnimationsAsync(),
  ]
};

export function HttpCLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}