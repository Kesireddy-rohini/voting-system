import { fireEvent, render, screen } from '@testing-library/react';
import VotingPage from './VotingPage';  // Adjust based on your component file

describe('Voting Page Tests', () => {
  test('renders voting page with candidate images and submit button', () => {
    render(<VotingPage />);
    const candidate1Image = screen.getByAltText(/Candidate 1/i);
    const candidate2Image = screen.getByAltText(/Candidate 2/i);
    const submitButton = screen.getByText(/Submit Your Vote/i);
    
    expect(candidate1Image).toBeInTheDocument();
    expect(candidate2Image).toBeInTheDocument();
    expect(submitButton).toBeInTheDocument();
  });

  test('ensures a candidate must be selected before submission', () => {
    render(<VotingPage />);
    const submitButton = screen.getByText(/Submit Your Vote/i);

    fireEvent.click(submitButton);
    expect(screen.getByText(/Please select a candidate/i)).toBeInTheDocument();
  });

  test('shows graphs after submitting a vote', () => {
    render(<VotingPage />);
    const candidate1Image = screen.getByAltText(/Candidate 1/i);
    const submitButton = screen.getByText(/Submit Your Vote/i);

    fireEvent.click(candidate1Image); // Simulate candidate selection
    fireEvent.click(submitButton); // Submit the vote

    const graphsContainer = screen.getByTestId('graphs'); // Ensure your div for graphs has data-testid="graphs"
    expect(graphsContainer).toBeVisible();
  });

  test('submit button turns green after voting', () => {
    render(<VotingPage />);
    const candidate1Image = screen.getByAltText(/Candidate 1/i);
    const submitButton = screen.getByText(/Submit Your Vote/i);

    fireEvent.click(candidate1Image); // Select candidate
    fireEvent.click(submitButton); // Submit vote

    expect(submitButton).toHaveClass('voted'); // Check for class that changes color
  });
});
