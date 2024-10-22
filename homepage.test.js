import { showAgenda } from './homepage';
require('text-encoding');
describe('showAgenda function', () => {
    let agenda;

    beforeEach(() => {
        document.body.innerHTML = `<p id="agenda-test" style="display: none;">Agenda Content</p>`;
        agenda = document.getElementById('agenda-test');
    });

    test('shows the agenda when clicked', () => {
        showAgenda('agenda-test');
        expect(agenda.style.display).toBe('block'); // Should now be visible
    });

    test('hides the agenda when clicked again', () => {
        // First click to show
        showAgenda('agenda-test');
        // Second click to hide
        showAgenda('agenda-test');
        expect(agenda.style.display).toBe('none'); // Should be hidden again
    });
});
