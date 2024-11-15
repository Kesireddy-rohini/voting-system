// Import necessary libraries
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Homepage from './Homepage'; // Replace with the actual path of your Homepage component

describe("Homepage Component Tests", () => {

    // Test Case 1: Homepage Loads Properly
    test("Homepage loads with all elements properly", () => {
        render(<Homepage />);

        // Check that the header, buttons, and images are present on page load
        expect(screen.getByText("USA Elections 2024: KNOW BETTER ABOUT POLITICIANS")).toBeInTheDocument();
        expect(screen.getByText("Donald Trump")).toBeInTheDocument();
        expect(screen.getByText("Kamala Harris")).toBeInTheDocument();
        expect(screen.getAllByRole("button", { name: /Agenda/i })).toHaveLength(2);
    });

    // Test Case 2: Candidate Agenda Toggle
    test("Clicking 'Agenda' button toggles full-page agenda overlay", () => {
        render(<Homepage />);

        // Click the Agenda button for Donald Trump
        const agendaButton = screen.getByText(/Agenda/i, { selector: 'button' });
        fireEvent.click(agendaButton);

        // Check that the overlay with agenda content is visible
        expect(screen.getByText("Donald Trump’s Complete Agenda")).toBeVisible();

        // Close the overlay
        const closeButton = screen.getByText(/Close/i, { selector: 'button' });
        fireEvent.click(closeButton);

        // Check that the overlay is hidden again
        expect(screen.queryByText("Donald Trump’s Complete Agenda")).not.toBeVisible();
    });

    // Test Case 3: Submit Q&A Request
    test("Submitting a question displays 'Thinking...' and then shows AI-generated answer", async () => {
        render(<Homepage />);

        // Find the input field and button
        const inputField = screen.getByPlaceholderText("Type your question here...");
        const submitButton = screen.getByText("Submit");

        // Type a question into the input field and submit
        fireEvent.change(inputField, { target: { value: "What are the main issues in the 2024 election?" } });
        fireEvent.click(submitButton);

        // Check for "Thinking..." text to appear
        expect(screen.getByText("Thinking...")).toBeInTheDocument();

        // Simulate AI response (Assuming AI response will replace "Thinking...")
        await screen.findByText(/AI-generated answer goes here/i); // Mocked response for test
    });

    // Test Case 4: Clear Input After Submission
    test("Clears input field after submitting a question", () => {
        render(<Homepage />);

        // Find the input field and submit button
        const inputField = screen.getByPlaceholderText("Type your question here...");
        const submitButton = screen.getByText("Submit");

        // Type a question into the input field and submit
        fireEvent.change(inputField, { target: { value: "Is voting compulsory?" } });
        fireEvent.click(submitButton);

        // Check that the input field is cleared after submission
        expect(inputField.value).toBe("");
    });
});
