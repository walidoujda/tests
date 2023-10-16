describe('Login Component', () => {
  beforeEach(() => {
    cy.visit('/login'); // Assurez-vous d'ajuster l'URL de la page de login en fonction de votre configuration
  });

  it('should fill out the login form and submit successfully', () => {
    // Remplir le formulaire avec des données valides
    cy.get('input[ng-reflect-name="email"]').should('exist').type('toto3@toto.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');

    // Soumettre le formulaire
    cy.get('button[type="submit"]').should('not.be.disabled').click();
    cy.url().should('eq', 'http://localhost:4200/sessions');
  });

  it('should show an error message for invalid input', () => {
    cy.get('input[ng-reflect-name="email"]').should('exist').type('test@toto.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('test!1234');
    cy.get('button[type="submit"]').click();
    cy.get('.error.ng-star-inserted').should('be.visible');
  });

  // Ajoutez d'autres tests en fonction de vos besoins
});
