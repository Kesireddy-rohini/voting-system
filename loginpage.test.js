import { render, screen, fireEvent } from '@testing-library/react';
import LoginPage from './LoginPage'; // Import your LoginPage component

describe('Login Page', () => {
  test('Valid Login', () => {
    render(<LoginPage />);
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123' } });
    fireEvent.click(screen.getByText(/login/i));
    expect(screen.getByText(/homepage/i)).toBeInTheDocument(); // Replace with your homepage redirection logic
  });

  test('Invalid Email Format', () => {
    render(<LoginPage />);
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'invalidemail' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123' } });
    fireEvent.click(screen.getByText(/login/i));
    expect(screen.getByText(/invalid email format/i)).toBeInTheDocument();
  });

  test('Empty Fields', () => {
    render(<LoginPage />);
    fireEvent.click(screen.getByText(/login/i));
    expect(screen.getByText(/please fill in all fields/i)).toBeInTheDocument();
  });

  test('Incorrect Password', () => {
    render(<LoginPage />);
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'wrongpassword' } });
    fireEvent.click(screen.getByText(/login/i));
    expect(screen.getByText(/incorrect password/i)).toBeInTheDocument();
  });

  test('Remember Me Functionality', () => {
    render(<LoginPage />);
    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.click(screen.getByLabelText(/remember me/i));
    fireEvent.click(screen.getByText(/login/i));
    expect(localStorage.getItem('rememberedEmail')).toBe('test@example.com'); // Replace with your implementation
  });
});
