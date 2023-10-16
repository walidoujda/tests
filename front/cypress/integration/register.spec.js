describe('Register Component', () => {
  beforeEach(() => {
    cy.visit('/register'); // Assurez-vous d'ajuster l'URL en fonction de votre configuration
  });

  it('should display the registration form', () => {
    cy.get('app-register').should('be.visible'); // Vérifie que le composant est visible
    cy.get('form').should('be.visible'); // Vérifie que le formulaire est visible
    cy.get('input[ng-reflect-name="email"]').should('be.visible'); // Vérifie que le champ email est visible
    cy.get('input[ng-reflect-name="firstName"]').should('be.visible'); // Vérifie que le champ firstName est visible
    cy.get('input[ng-reflect-name="lastName"]').should('be.visible'); // Vérifie que le champ lastName est visible
    cy.get('input[ng-reflect-name="password"]').should('be.visible'); // Vérifie que le champ password est visible
    cy.get('button[type="submit"]').should('be.visible'); // Vérifie que le bouton de soumission est visible
  });

  it('should submit the registration form with valid data', () => {
    // Remplir le formulaire avec des données valides
    cy.get('input[ng-reflect-name="email"]').type('test@example.com');
    cy.get('input[ng-reflect-name="firstName"]').type('walid');
    cy.get('input[ng-reflect-name="lastName"]').type('Doe');
    cy.get('input[ng-reflect-name="password"]').type('password123');

    // Soumettre le formulaire
    cy.get('button[type="submit"]').click();

    // Assurez-vous que la redirection s'est bien produite (ajustez l'URL selon votre cas)
    cy.url().should('eq', 'URL attendue après la soumission du formulaire');
  });

  it('should display an error message for invalid input', () => {
    // Laissez le formulaire vide pour provoquer une erreur
    cy.get('input[ng-reflect-name="email"]').type('test@example.com');
    cy.get('input[ng-reflect-name="firstName"]').type('walid');
    cy.get('input[ng-reflect-name="lastName"]').type('Doe');
    cy.get('input[ng-reflect-name="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    // Assurez-vous que le message d'erreur est affiché (ajustez le sélecteur CSS en fonction de votre implémentation)
    cy.get('.error.ng-star-inserted').should('be.visible');

    // Vous pouvez également ajouter des assertions supplémentaires pour vérifier le contenu du message d'erreur
  });

  // Ajoutez d'autres tests en fonction de vos besoins, par exemple pour tester les validations de champs
});
