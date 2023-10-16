import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { DetailComponent } from './detail.component';
import { SessionService } from '../../../../services/session.service';
import { TeacherService } from '../../../../services/teacher.service';
import { SessionApiService } from '../../services/session-api.service';
import { expect } from '@jest/globals';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailComponent],
      imports: [ReactiveFormsModule, FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (param: string) => '1',
              },
            },
          },
        },
        FormBuilder,
        {
          provide: SessionService,
          useValue: {
            delete: () => of(null),
            participate: () => of(null),
            unParticipate: () => of(null),
            isLogged : false,
            sessionInformation : {
                token: 'token',
                type: 'type',
                id: 1,
                username: 'username',
                firstName: 'firstName',
                lastName: 'lastName',
                admin: true
            },
          },
        },
         SessionApiService,
        TeacherService,
        {
          provide: MatSnackBar,
          useValue: {
            open: () => {},
          },
        },
        {
          provide: Router,
          useValue: {
            navigate: () => {},
          },
        },
      ],
    });

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call delete() and navigate when delete() is called', () => {
    const sessionApiService = TestBed.inject(SessionApiService);
    const matSnackBar = TestBed.inject(MatSnackBar);
    const router = TestBed.inject(Router);
    jest.spyOn(sessionApiService, 'delete').mockImplementation(() => of(null));
    jest.spyOn(matSnackBar, 'open');
    jest.spyOn(router, 'navigate');

    component.delete();

    expect(sessionApiService.delete).toHaveBeenCalled();
    expect(matSnackBar.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
    expect(router.navigate).toHaveBeenCalledWith(['sessions']);
  });
});
