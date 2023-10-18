describe('Register Component', () => {
  beforeEach(() => {
    cy.visit('/register');
  });

  it('should display the registration form', () => {
    cy.get('app-register').should('be.visible');
    cy.get('form').should('be.visible');
    cy.get('input[ng-reflect-name="email"]').should('be.visible');
    cy.get('input[ng-reflect-name="firstName"]').should('be.visible');
    cy.get('input[ng-reflect-name="lastName"]').should('be.visible');
    cy.get('input[ng-reflect-name="password"]').should('be.visible');
    cy.get('button[type="submit"]').should('be.visible');
  });

  it('should submit the registration form with valid data', () => {
    cy.get('input[ng-reflect-name="email"]').should('exist').type('test7777@example.com');
    cy.get('input[ng-reflect-name="firstName"]').should('exist').type('walid');
    cy.get('input[ng-reflect-name="lastName"]').should('exist').type('Doe');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('password123');

    cy.get('button[type="submit"]').click();

    cy.url().should('eq', 'http://localhost:4200/register');
  });

  it('should display an error message for invalid input', () => {
    cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifi@gmail.com');
    cy.get('input[ng-reflect-name="firstName"]').should('exist').type('walid');
    cy.get('input[ng-reflect-name="lastName"]').should('exist').type('Doe');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('password123');
    cy.get('button[type="submit"]').click();
    cy.get('.error.ng-star-inserted').should('be.visible');

  });
});
