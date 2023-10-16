import { of } from 'rxjs';
import { MeComponent } from './me.component';
import { expect } from '@jest/globals';

jest.mock('@angular/router');
jest.mock('../../services/session.service');
jest.mock('@angular/material/snack-bar');
jest.mock('../../services/user.service');

describe('MeComponent', () => {
  let component: MeComponent;
  let mockRouter: any;
  let mockSessionService: any;
  let mockMatSnackBar: any;
  let mockUserService: any;

  beforeEach(() => {
    mockRouter = { navigate: jest.fn() };
    mockSessionService = { sessionInformation: { id: '1' }, logOut: jest.fn() };
    mockMatSnackBar = { open: jest.fn() };
    mockUserService = { getById: jest.fn().mockReturnValue(of({})), delete: jest.fn().mockReturnValue(of(null)) };

    component = new MeComponent(
      mockRouter,
      mockSessionService,
      mockMatSnackBar,
      mockUserService
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get user data on initialization', () => {
    const user = { name: 'Test User' };
    mockUserService.getById.mockReturnValue(of(user));
    component.ngOnInit();
    expect(component.user).toEqual(user);
    expect(mockUserService.getById).toHaveBeenCalledWith('1');
  });

  it('should handle back', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should handle delete', () => {
    component.delete();
    expect(mockUserService.delete).toHaveBeenCalledWith('1');
    expect(mockSessionService.logOut).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
    expect(mockMatSnackBar.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
  });
});
