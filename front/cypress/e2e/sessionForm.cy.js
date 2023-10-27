describe('FormComponent', () => {

  beforeEach(() => {

     /* cy.visit('/login');
      cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifi@gmail.com');
      cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

      cy.get('button[type="submit"]').should('not.be.disabled').click();*/

  });

  it('should create a new session', () => {
    cy.visit('/sessions',cy.g);

    cy.get('input[ng-reflect-name="name"]').type('Nouvelle session');
    cy.get('input[ng-reflect-name="date"]').type('2023-10-22');
    cy.get('input[ng-reflect-name="teacher_id"]').select('1');
    cy.get('input[ng-reflect-name="description"]').type('Description de la nouvelle session');

    cy.get('button[type="submit"]').should('not.be.disabled').click();

    cy.contains('Session created !');
  });

});
