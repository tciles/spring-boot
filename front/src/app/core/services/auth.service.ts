import {inject, Injectable} from '@angular/core';
import {API_PASSWORD, API_USER} from '../../app.config';

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private username = inject(API_USER);
  private password = inject(API_PASSWORD);

  getAuthToken(): string {
    let token = "Basic ";
    token += btoa(`${this.username}:${this.password}`)

    return token;
  }
}
