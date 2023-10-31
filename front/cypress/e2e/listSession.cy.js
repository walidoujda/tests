describe('Tests d\'intégration - Liste de Sessions', () => {
  before(() => {
    cy.visit('/login');
    cy.get('input[name="email"]').type('votre-email@example.com');
    cy.get('input[name="password"]').type('votre-mot-de-passe');
    cy.get('button[type="submit"]').click();
  });

  beforeEach(() => {
    cy.visit('/sessions');
  });

  it('Doit afficher la liste de sessions', () => {
    cy.get('.session-item').should('exist');
    cy.get('.session-item').should('have.length.gt', 0);
  });
});
