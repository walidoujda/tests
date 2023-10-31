describe('Tests d\'intégration - ListComponent', () => {


  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[ng-reflect-name="email"]').should('exist').type('walidzerrifiCY@example.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

    cy.get('button[type="submit"]').should('not.be.disabled').click();

  });

  it('Doit afficher la liste de sessions', () => {
    cy.get('app-list').should('exist');
    cy.get('.list').should('exist');
    cy.get('.list .items').should('have.length.gt', 0);
  });

  it('Doit afficher les détails d\'une session au clic', () => {
    cy.get('.list .items').contains('Detail').click();
  });
});
