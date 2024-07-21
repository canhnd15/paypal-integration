import Button from "../components/Button.jsx";
import {useNavigate, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Container from "../components/Container.jsx";
import DisplayInfo from "../components/DisplayInfo.jsx";

function SuccessPage() {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [response, setResponse] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    const [paymentId] = useState(searchParams.get("paymentId"));
    const [payerId] = useState(searchParams.get("PayerID"));

useEffect(() => {
    const reqData = {
        paymentId: paymentId,
        payerId: payerId
    };

    const sendPostRequest = async () => {
        try {
            const res = await axios.post('http://localhost:8080/api/v1/payment/execute', reqData);
            setResponse(res.data);
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };
    sendPostRequest();
}, [paymentId, payerId]);

    const redirectToPaymentPage = () => {
        navigate("/")
    }

    return <>
        <Container>
            {
                isLoading ? <h1>Payment is executing...</h1> : (response !== null ?
                    <div>
                        <h1>Payment is made successfully!</h1>
                        <DisplayInfo label={"Payment ID:"} value={response.data.id}/>
                        <DisplayInfo label={"Payer's name:"} value={response.data.fullName}/>
                        <DisplayInfo label={"Payer's email:"} value={response.data.email}/>
                    </div>
                    : <h1>Payment is fail!</h1>)
            }
            <p>{error ? "Error: " + error : ""}</p>
            <Button onClick={redirectToPaymentPage}>Back to homepage</Button>
        </Container>
    </>
}

export default SuccessPage;