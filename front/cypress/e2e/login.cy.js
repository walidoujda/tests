describe('Login Component', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('should fill out the login form and submit successfully', () => {
    cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifi@example.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

    cy.get('button[type="submit"]').should('not.be.disabled').click();
    cy.url().should('eq', 'http://localhost:4200/sessions');
  });

  it('should show an error message for invalid input', () => {
    cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifi@example.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!12345');
    cy.get('button[type="submit"]').click();
    cy.get('.error.ng-star-inserted').should('be.visible');
  });

});
