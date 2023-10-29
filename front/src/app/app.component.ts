import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { SessionInformation } from './interfaces/sessionInformation.interface';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
      if(sessionStorage.getItem('isLogged') === 'true' && sessionStorage.getItem('currentUser') != null){
        const currentUserData = sessionStorage.getItem('currentUser');
        this.sessionService.isLogged = true;
        if (currentUserData !== null) {
          this.sessionService.sessionInformation = JSON.parse(currentUserData);
        }
      }
  }

  public $isLogged(): Observable<boolean> {
    console.log(this.sessionService.$isLogged())
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }
}
