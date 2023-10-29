import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  constructor() {
    this.checkSessionStorage();
  }
  public $isLogged(): Observable<boolean> {
    console.log('je suis la la la ')
    console.log(this.isLoggedSubject.asObservable())
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    sessionStorage.setItem('currentUser', JSON.stringify(user));
    sessionStorage.setItem('isLogged', 'true');
    this.sessionInformation = user;
    this.isLogged = true;
    this.next(true);
  }

  public logOut(): void {
    sessionStorage.removeItem('currentUser');
    sessionStorage.removeItem('isLogged');
    this.sessionInformation = undefined;
    this.isLogged = false;
    this.next(false);
  }

  private next(isLogged: boolean): void {
    console.log(this.isLogged);
    this.isLoggedSubject.next(this.isLogged);
  }

  private checkSessionStorage(): void {
    const isLogged = sessionStorage.getItem('isLogged');
    if (isLogged === 'true') {
      this.isLoggedSubject.next(true);
    }
  }
}
