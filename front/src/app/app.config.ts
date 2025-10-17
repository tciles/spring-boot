import {
  ApplicationConfig,
  InjectionToken,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {authInterceptor} from './core/interceptors/auth.interceptor';

export const BASE_API_URL_TOKEN = new InjectionToken<string>("BASE_API_URL_TOKEN");
export const API_USER = new InjectionToken<string>("API_USER");
export const API_PASSWORD = new InjectionToken<string>("API_PASSWORD");

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([authInterceptor])
    ),
    { provide: BASE_API_URL_TOKEN, useValue: "http://localhost:8080/api" },

    // Just for test
    { provide: API_USER, useValue: "user" },
    { provide: API_PASSWORD, useValue: "password" },
  ]
};
