/**
 * @jest-environment jsdom
 */
const fs = require('fs');
const path = require('path');
const { JSDOM } = require('jsdom');
const dom = new JSDOM(`<!DOCTYPE html><html><body></body></html>`, {
  url: "http://localhost",
  contentType: "text/html",
  includeNodeLocations: true,
  pretendToBeOnLine: true,
  runScripts: "dangerously",
  resources: "usable"
});
// Load the HTML and JS
const html = fs.readFileSync(path.resolve(__dirname, 'loginpage.html'), 'utf8');
const script = fs.readFileSync(path.resolve(__dirname, 'loginpage.js'), 'utf8');

global.window = dom.window;
global.document = dom.window.document;
global.TextEncoder = require('util').TextEncoder; // Add this line

global.TextEncoder = require('util').TextEncoder;
global.TextDecoder = require('util').TextDecoder;

describe('Login Page', () => {
  let dom, document, window;

  beforeEach(() => {
    // Load the HTML in the JSDOM environment
    dom = new JSDOM(html, { runScripts: 'dangerously' });
    document = dom.window.document;
    window = dom.window;

    // Evaluate the script inside the JSDOM window
    window.eval(script);
  });

  test('should render the login form', () => {
    const form = document.querySelector('form');
    expect(form).not.toBeNull(); // Form should exist
  });

  test('should have email and password fields', () => {
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');

    expect(emailInput).not.toBeNull();
    expect(passwordInput).not.toBeNull();
  });

  test('should trigger handleLogin on form submission', () => {
    const form = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');

    // Mock the alert function
    const alertMock = jest.spyOn(window, 'alert').mockImplementation(() => {});

    // Fill in email and password
    emailInput.value = 'test@example.com';
    passwordInput.value = 'password123';

    // Submit the form
    form.dispatchEvent(new window.Event('submit'));

    expect(alertMock).toHaveBeenCalledWith('Login successful!');
  });

  test('should prevent form submission by default', () => {
    const form = document.getElementById('loginForm');

    const event = new window.Event('submit');
    event.preventDefault = jest.fn();

    // Submit the form
    form.dispatchEvent(event);

    expect(event.preventDefault).toHaveBeenCalled();
  });
});