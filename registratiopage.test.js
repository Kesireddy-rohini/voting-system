import { render, screen, fireEvent } from '@testing-library/react';
import RegistrationPage from './RegistrationPage'; // Import your RegistrationPage component

describe('Registration Page', () => {
  test('Valid Registration', () => {
    render(<RegistrationPage />);
    fireEvent.change(screen.getByPlaceholderText(/full name/i), { target: { value: 'John Doe' } });
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123!' } });
    fireEvent.change(screen.getByPlaceholderText(/date of birth/i), { target: { value: '2000-01-01' } });
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/registration successful/i)).toBeInTheDocument(); // Replace with redirection logic
  });

  test('Missing Required Fields', () => {
    render(<RegistrationPage />);
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/this field is required/i)).toBeInTheDocument();
  });

  test('Invalid Email Format', () => {
    render(<RegistrationPage />);
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'invalidemail' } });
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/invalid email format/i)).toBeInTheDocument();
  });

  test('Password Strength Validation', () => {
    render(<RegistrationPage />);
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'weakpass' } });
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/password must be at least 8 characters long/i)).toBeInTheDocument();
  });

  test('Age Eligibility Check', () => {
    render(<RegistrationPage />);
    fireEvent.change(screen.getByPlaceholderText(/date of birth/i), { target: { value: '2010-01-01' } });
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/you must be at least 18 years old to register/i)).toBeInTheDocument();
  });

  test('Duplicate Email', () => {
    render(<RegistrationPage />);
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/this email is already registered/i)).toBeInTheDocument();
  });

  test('Terms and Conditions Checkbox', () => {
    render(<RegistrationPage />);
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/you must accept the terms and conditions/i)).toBeInTheDocument();
  });

  test('Successful Redirection', () => {
    render(<RegistrationPage />);
    fireEvent.change(screen.getByPlaceholderText(/full name/i), { target: { value: 'John Doe' } });
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123!' } });
    fireEvent.change(screen.getByPlaceholderText(/date of birth/i), { target: { value: '2000-01-01' } });
    fireEvent.click(screen.getByText(/register/i));
    expect(screen.getByText(/login/i)).toBeInTheDocument(); // Replace with redirection logic
  });
});
