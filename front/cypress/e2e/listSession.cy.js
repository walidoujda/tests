describe('Tests d\'intégration - Liste de Sessions', () => {
  before(() => {
    // Effectuez d'abord la connexion de l'utilisateur.
    cy.visit('/login'); // Assurez-vous que c'est l'URL de la page de connexion.

    // Remplissez le formulaire de connexion avec des informations valides.
    cy.get('input[name="email"]').type('votre-email@example.com');
    cy.get('input[name="password"]').type('votre-mot-de-passe');
    cy.get('button[type="submit"]').click();

    // Attendez la redirection ou tout autre indicateur de connexion réussie.
    // Vous pouvez utiliser cy.url(), cy.get(), ou d'autres méthodes selon votre application.
  });

  beforeEach(() => {
    // Après la connexion réussie, accédez à la page de la liste de sessions.
    cy.visit('/sessions'); // Assurez-vous que c'est l'URL de la liste de sessions.
  });

  it('Doit afficher la liste de sessions', () => {
    // Maintenant, vous êtes connecté et pouvez vérifier la liste de sessions.
    cy.get('.session-item').should('exist');
    cy.get('.session-item').should('have.length.gt', 0);
  });

  // Ajoutez d'autres tests en fonction de vos besoins.
});
