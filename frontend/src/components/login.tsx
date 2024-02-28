type Props = {
    log: () => void,
}
export default function Login(props:Props) {

    function login(){
        props.log()
    }
    return(
        <>
            <p> Sie sollen angemeldet sein um den Service der Seite nutzen zu können</p>
            <button onClick={login}>Melden Sie sich bitte an</button>
        </>)
}
