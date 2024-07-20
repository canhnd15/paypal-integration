import Button from "../components/Button.jsx";
import {useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function SuccessPage() {
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

        console.log(reqData);

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

    return <>
        {
            isLoading ? <h1>Payment is executing...</h1> : (response ? <h1>Payment is made successfully!</h1> : <h1>Payment is fail!</h1>)
        }
        <Button disabled={isLoading}>Back to homepage</Button>
        <h2>{error ? "Error: " + error : ""}</h2>
    </>
}

export default SuccessPage;