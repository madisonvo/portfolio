import {React} from "react";
import "./NavBar.css";
import logo from "../images/quiztopia-logo.png";

export const NavBar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-left">
                <a href="/">
                    <img style={{width: "150px"}} src={logo} alt="quiztopia-logo"/>
                </a>
            </div>
            <div className="navbar-right">
                <ul className="nav-links">
                    <li>
                        <a href="/">Home</a>
                    </li>
                </ul>
            </div>
        </nav>
    );
}
