import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import Registration from './Registration'; // Adjust the path to your Registration component

describe("Registration Page Tests", () => {
    // Test 1: Check that the registration page loads correctly
    test("Registration page loads properly with all elements", () => {
        render(<Registration />);
        
        // Check if all required elements are in the document
        expect(screen.getByLabelText("Full Name")).toBeInTheDocument();
        expect(screen.getByLabelText("Email")).toBeInTheDocument();
        expect(screen.getByLabelText("Password")).toBeInTheDocument();
        expect(screen.getByLabelText("Confirm Password")).toBeInTheDocument();
        expect(screen.getByText("Register")).toBeInTheDocument();
    });

    // Test 2: Successful registration
    test("Successful registration with valid details", () => {
        render(<Registration />);
        
        // Enter valid details
        fireEvent.change(screen.getByLabelText("Full Name"), { target: { value: "John Doe" } });
        fireEvent.change(screen.getByLabelText("Email"), { target: { value: "john@example.com" } });
        fireEvent.change(screen.getByLabelText("Password"), { target: { value: "securepassword" } });
        fireEvent.change(screen.getByLabelText("Confirm Password"), { target: { value: "securepassword" } });

        fireEvent.click(screen.getByText("Register"));

        // Verify redirection or success message
        expect(screen.getByText("Registration successful")).toBeInTheDocument(); // Adjust based on success handling
    });

    // Test 3: Required fields validation
    test("Display errors when submitting empty fields", () => {
        render(<Registration />);
        
        fireEvent.click(screen.getByText("Register")); // Submit empty form

        expect(screen.getByText("Full Name is required")).toBeInTheDocument();
        expect(screen.getByText("Email is required")).toBeInTheDocument();
        expect(screen.getByText("Password is required")).toBeInTheDocument();
        expect(screen.getByText("Confirm Password is required")).toBeInTheDocument();
    });

    // Test 4: Password length validation
    test("Display error when password is too short", () => {
        render(<Registration />);
        
        fireEvent.change(screen.getByLabelText("Password"), { target: { value: "short" } });
        fireEvent.click(screen.getByText("Register"));

        expect(screen.getByText("Password must be at least 8 characters long")).toBeInTheDocument();
    });

    // Test 5: Password match validation
    test("Display error when passwords do not match", () => {
        render(<Registration />);
        
        fireEvent.change(screen.getByLabelText("Password"), { target: { value: "password123" } });
        fireEvent.change(screen.getByLabelText("Confirm Password"), { target: { value: "differentpass" } });
        fireEvent.click(screen.getByText("Register"));

        expect(screen.getByText("Passwords do not match")).toBeInTheDocument();
    });

    // Test 6: Duplicate email check
    test("Display error when email already exists", () => {
        render(<Registration />);
        
        // Enter an email that's already registered
        fireEvent.change(screen.getByLabelText("Email"), { target: { value: "existinguser@example.com" } });
        fireEvent.click(screen.getByText("Register"));

        expect(screen.getByText("Email already exists")).toBeInTheDocument();
    });

    // Test 7: Redirect to login page
    test("Redirect to login page on clicking Login link", () => {
        render(<Registration />);
        
        fireEvent.click(screen.getByText("Login"));

        expect(screen.getByText("Login to your account")).toBeInTheDocument(); // Verify login page loaded
    });
});
