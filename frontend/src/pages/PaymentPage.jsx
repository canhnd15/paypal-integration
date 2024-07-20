import {useForm} from "react-hook-form";
import Form from "../components/Form.jsx";
import FormRow from "../components/StyledFormRow.jsx";
import Input from "../components/Input.jsx";
import Button from "../components/Button.jsx";
import styled from "styled-components";

const Container = styled.div`
    max-width: 1280px;
    margin: 0 auto;
    padding: 2rem;
    text-align: center;
`

const Heading = styled.h1`
    padding-bottom: 30px;
`

function PaymentPage() {
    const {register, handleSubmit, formState: {errors}} = useForm();

    const onSubmit = async (data) => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/payment/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const result = await response.json();
                let linkHref = "";
                result?.data?.links.map((link) => {
                    if (link.rel === 'approval_url') linkHref = link.href;
                });
                window.open(linkHref, '_blank', 'noopener,noreferrer');
            } else {
                console.error('Error:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <Container>
            <Heading>David Store</Heading>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <FormRow
                    label={"Payment method"}
                    isRequired={true}
                    error={errors?.method?.message}
                >
                    <Input
                        type="text"
                        id="method"
                        defaultValue={"Paypal"}
                        {...register('method', {required: 'Payment method is required'})}
                    />
                </FormRow>

                <FormRow
                    label={"Total amount"}
                    isRequired={true}
                    error={errors?.amount?.message}
                >
                    <Input
                        type="text"
                        id="total"
                        defaultValue={"10.0"}
                        {...register('total', {required: 'Total amount is required'})}
                    />
                </FormRow>

                <FormRow
                    label={"Currency"}
                    isRequired={true}
                    error={errors?.currency?.message}
                >
                    <Input
                        type="text"
                        id="currency"
                        defaultValue={"USD"}
                        {...register('currency', {required: 'Currency is required'})}
                    />
                </FormRow>

                <FormRow
                    label={"Description"}
                    isRequired={true}
                    error={errors?.description?.message}
                >
                    <Input
                        type="text"
                        id="description"
                        defaultValue={"Ip15 promax from David"}
                        {...register('description', {required: 'Description is required'})}
                    />
                </FormRow>
                <FormRow>
                    <Button type={"submit"}>Pay with PayPal</Button>
                </FormRow>
            </Form>
        </Container>
    )
}

export default PaymentPage;