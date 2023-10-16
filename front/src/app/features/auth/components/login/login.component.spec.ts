import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { expect } from '@jest/globals';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SessionService } from 'src/app/services/session.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [ReactiveFormsModule, FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [AuthService, FormBuilder, SessionService],
    });

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should call authService.login and navigate to /sessions on successful login', () => {
    const authService = TestBed.inject(AuthService);
    const sessionService = TestBed.inject(SessionService);
    const router = TestBed.inject(Router);
    const loginRequest = { email: 'test@example.com', password: 'password' };
    const sessionInformation = {   token: 'token',
      type: 'type',
      id: 0,
      username: 'username',
      firstName: 'firstName',
      lastName: 'lastName',
      admin: true
    };

    jest.spyOn(authService, 'login').mockImplementation(() => of(sessionInformation));
    jest.spyOn(sessionService, 'logIn');
    jest.spyOn(router, 'navigate');

    component.form.setValue(loginRequest);
    component.submit();

    expect(authService.login).toHaveBeenCalledWith(loginRequest);
    expect(sessionService.logIn).toHaveBeenCalledWith(sessionInformation);
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should set onError to true on login error', () => {
    const authService = TestBed.inject(AuthService);
    const error = new Error('Login error');

    jest.spyOn(authService, 'login').mockImplementation(() => throwError(() => new Error('Login error')));

    component.submit();

    expect(component.onError).toBeTruthy();
  });

  it('should match snapshot', () => {
    expect(fixture).toMatchSnapshot();
  });

});
