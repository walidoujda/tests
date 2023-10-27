describe('DetailComponent', () => {
  before(() => {
    cy.visit('/login');
    cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifi@gmail.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

    cy.get('button[type="submit"]').should('not.be.disabled').click();
    cy.get('.list .items').contains('Detail').click();
  });

  it('should display session details', () => {
    cy.url().should('contain', 'http://localhost:4200/sessions/detail');
  });

});
