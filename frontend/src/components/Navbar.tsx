import styled from 'styled-components';
import {NavLink} from "react-router-dom";

export default function NavBar() {
    return (

        <div>
            <StyledNav>
                <NavContainer>
                    <Heading>Home Library</Heading>
                    <FlexContainer>

                        <NavLinkAdd to="/books/add"> + New Book</NavLinkAdd>
                        <NavLinks to="/">Home</NavLinks>
                        <NavLinks to="/list">List of Books</NavLinks>
                        <NavLinks to="/favorites">Favorites</NavLinks>
                        <NavLinks to="/kontakt">Kontakt</NavLinks>

                    </FlexContainer>
                </NavContainer>
            </StyledNav>

        </div>
    )
}


const StyledNav = styled.nav`
    background-color: #4b5563;
    height: auto;
`;

const NavContainer = styled.div`
    max-width: 120rem;
    padding-left: 1rem;
    padding-right: 0.5rem;
    margin: 0;
    display: flex;
    justify-content: space-between;
`;

const FlexContainer = styled.div`
    display: flex;
    flex-wrap: wrap;
    padding: 0.75rem 0 0.75rem 0;
    max-width: 80vw;
    align-items: center;
    justify-content: space-around;
`;


const NavLinks = styled(NavLink)`
    color: #ffffff;
    background-color: #4b5563;
    width: auto;
    height: auto;
    text-decoration: none;
    border-radius: 0.375rem;
    padding: 1vw;
    font-size: 1.5vw;
    font-weight: 500;
    margin-right: 0.3vw;
`;

const Heading = styled.h1`
    font-size: 5vw;
    color: #FFF;
    font-family: "Party LET", serif;
    font-style: normal;
    font-weight: 200;
    line-height: normal;
`;
const NavLinkAdd = styled(NavLinks)`
    color: #ffffff;
    background-color: darkmagenta;
    border-radius: 0.975rem;
    padding: 0.55rem 1rem;
    font-size: 1.7vw;
    font-weight: 500;
    margin-right: 1rem;
`;
