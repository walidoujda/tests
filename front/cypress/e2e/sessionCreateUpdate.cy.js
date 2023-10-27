describe('FormComponent', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('input[ng-reflect-name="email"]').should('exist').type('admin@example.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

    cy.get('button[type="submit"]').should('not.be.disabled').click();
    cy.url().should('include', '/sessions');
    })
  it('Access is denied for users who do not have administrator privileges', () => {
    cy.visit('/login')
    cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifi@example.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

    cy.get('button[type="submit"]').should('not.be.disabled').click();
    cy.url().should('include', '/sessions');
    cy.url().should('eq', 'http://localhost:4200/sessions');
  });
  it('should create session', () => {

    cy.visit('http://localhost:4200/sessions/create')
    cy.get('input[ng-reflect-name="name"]').type('testgfjhgfhjfghgfhj2');
    cy.get('input[ng-reflect-name="date"]').type('1984-04-21');
    cy.get('mat-select[ng-reflect-name="teacher_id"]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[ng-reflect-name="description"]').type('monia 7mara');
    cy.get('button[type="submit"]').click();
  });
  it('should update session', () => {

    cy.visit('http://localhost:4200/sessions/create/1')
    cy.get('input[ng-reflect-name="name"]').type('test');
    cy.get('input[ng-reflect-name="date"]').type('1984-04-21');
    cy.get('mat-select[ng-reflect-name="teacher_id"]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[ng-reflect-name="description"]').type('monia 7mara');
    cy.get('button[type="submit"]').click();
  });

});
