import React from 'react';
import './NavBar.css'; // importing NavBar css file
import logo from '../images/quiztopia-logo.png'; // importing quiztopia logo

export const NavBar = () => {
    return (
        // navbar
        <nav className="navbar">
            {/* left side of navbar - logo */}
            <div className="navbar-left">
                <img style={{width: '100px'}} src={logo} alt='quiztopia_logo'/>
            </div>

            {/* right side of navbar - home and leaderboard */}
            <div className="navbar-right">
                <ul className="nav-links">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        <a href="/leaderboard">Leaderboard</a>
                    </li>
                </ul>
            </div>


        </nav>
    );
}
