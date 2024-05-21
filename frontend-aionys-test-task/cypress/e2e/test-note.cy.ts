describe('Notes successfully work', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200');
  })

  it('Notes name is present', () => {
    cy.contains("Notes");

  })

  it('Table have content', () => {
    cy.get('table tbody tr').should('be.visible');
  })

  it('Get endpoint OK response', () => {
    cy.request('GET', 'http://localhost:8080/notes').then((response) => {
      //Expecting the response status code to be 200
      expect(response.status).to.eq(200);
    })
  })

  it("Must add Note", () => {
    cy.get('#noteTitle').type('Test')
    cy.get('#noteText').type('Test Text')
    cy.get('button.btn.btn-primary').contains('Save Note').click();
    cy.get('table tbody tr').should('have.length', 5);
  })

  it("Must update Note", () => {
    cy.get('table tbody tr').eq(4).contains('Edit').click();
    cy.get('table tbody tr input').eq(0).type('{selectAll} Udated Text ');
    cy.get('table tbody tr input').eq(1).type('{selectAll} Udated Test Text');
    cy.get('table tbody tr').eq(4).contains('Submit').click();
    cy.get('table tbody tr').contains('Udated Text');
    cy.get('table tbody tr').contains('Udated Test Text');
  })

  it("Find Note by id", () => {
    cy.get('#noteId').type('{selectAll} 1')
    cy.get('button.btn.btn-primary').contains('Find Note').click();
    cy.get('table tbody tr').contains('First note');
    cy.get('table tbody tr').contains('Text about first note');
    cy.get('table tbody tr').should('have.length', 1);
  })

  it("Must delete Note", () => {
    cy.get('table tbody tr').eq(4).contains('Delete').click();
    cy.get('table tbody tr').should('have.length', 4);
  })

  it("Must change page language", () => {
    cy.get('mat-form-field').click();
    cy.get('#mat-option-2').click();
    cy.contains("Нотатки");
  })

})
