import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import Login from './Login'; // Adjust the path to your Login component

describe("Login Page Tests", () => {
    // Test 1: Check that the login page loads correctly
    test("Login page loads properly with all elements", () => {
        render(<Login />);
        
        // Check if all the required elements are in the document
        expect(screen.getByLabelText("Email")).toBeInTheDocument();
        expect(screen.getByLabelText("Password")).toBeInTheDocument();
        expect(screen.getByText("Login")).toBeInTheDocument();
        expect(screen.getByText("Register")).toBeInTheDocument();
    });

    // Test 2: Valid login
    test("Valid login with correct credentials", () => {
        render(<Login />);
        
        // Simulate entering valid credentials
        fireEvent.change(screen.getByLabelText("Email"), { target: { value: "user@example.com" } });
        fireEvent.change(screen.getByLabelText("Password"), { target: { value: "validpassword" } });
        
        // Simulate form submission
        fireEvent.click(screen.getByText("Login"));
        
        // Expect redirection or some success indication
        expect(screen.getByText("Welcome, User")).toBeInTheDocument(); // Modify based on your actual success handling
    });

    // Test 3: Invalid login - Empty fields
    test("Display error when submitting empty fields", () => {
        render(<Login />);
        
        fireEvent.click(screen.getByText("Login")); // Submit empty form

        expect(screen.getByText("Email is required")).toBeInTheDocument();
        expect(screen.getByText("Password is required")).toBeInTheDocument();
    });

    // Test 4: Email format validation
    test("Display error for invalid email format", () => {
        render(<Login />);
        
        fireEvent.change(screen.getByLabelText("Email"), { target: { value: "invalidemail" } });
        fireEvent.click(screen.getByText("Login"));

        expect(screen.getByText("Enter a valid email")).toBeInTheDocument();
    });

    // Test 5: Redirect to registration page
    test("Redirect to registration page on clicking Register", () => {
        render(<Login />);
        
        fireEvent.click(screen.getByText("Register"));

        expect(screen.getByText("Create an Account")).toBeInTheDocument(); // Verify registration page loaded
    });
});
