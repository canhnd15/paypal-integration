import Button from "../components/Button.jsx";
import Container from "../components/Container.jsx";
import {useNavigate} from "react-router-dom";

function CancelPage() {
    const navigate = useNavigate();

    const redirectToPaymentPage = () => {
        navigate("/")
    }

    return <>
        <Container>
            <h1>Payment is canceled.</h1>
            <Button onClick={redirectToPaymentPage}>Back to homepage</Button>
        </Container>
    </>
}

export default CancelPage;