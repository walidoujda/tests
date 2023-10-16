describe('Login Component', () => {
  beforeEach(() => {
    cy.visit('/login'); // Assurez-vous d'ajuster l'URL de la page de login en fonction de votre configuration
  });

  it('should fill out the login form and submit successfully', () => {
    // Remplir le formulaire avec des données valides
    cy.get('input[ng-reflect-name="email"]').should('exist').type('test@example.com');
    cy.get('input[ng-reflect-name="password"]').should('exist').type('password123');

    // Soumettre le formulaire
    cy.get('button[type="submit"]').should('not.be.disabled').click();

    // Assurez-vous que la redirection s'est bien produite (ajustez l'URL selon votre cas)
    cy.url().should('eq', 'URL attendue après la soumission du formulaire');

    // Vous pouvez également ajouter des assertions supplémentaires en fonction de votre logique
  });

  it('should show an error message for invalid input', () => {
    // Laissez le formulaire vide pour provoquer une erreur
    cy.get('button[type="submit"]').click();

    // Assurez-vous que le message d'erreur est affiché
    cy.get('.error-message').should('be.visible'); // Assurez-vous d'ajuster le sélecteur CSS en fonction de votre implémentation

    // Vous pouvez également ajouter des assertions supplémentaires pour vérifier le contenu du message d'erreur
  });

  // Ajoutez d'autres tests en fonction de vos besoins
});
