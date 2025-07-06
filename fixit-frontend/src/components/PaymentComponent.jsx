import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';

const PaymentComponent = ({ amount, serviceRequestId, onSuccess, onError }) => {
    const dispatch = useDispatch();

    const initializePayment = async () => {
        try {
            // Create order on backend
            const response = await axios.post('http://localhost:8080/api/payments/create-order', {
                amount: amount,
                currency: 'INR',
                receipt: `receipt_${serviceRequestId}`,
                serviceRequestId: serviceRequestId
            });

            const { orderId, keyId, amount: responseAmount, currency } = response.data;

            // Initialize Razorpay
            const options = {
                key: keyId,
                amount: responseAmount * 100, // amount in smallest currency unit
                currency: currency,
                name: 'FixIt Services',
                description: 'Service Payment',
                order_id: orderId,
                handler: async function (response) {
                    try {
                        // Verify payment on backend
                        await axios.post('http://localhost:8080/api/payments/verify', null, {
                            params: {
                                orderId: response.razorpay_order_id,
                                paymentId: response.razorpay_payment_id,
                                signature: response.razorpay_signature
                            }
                        });
                        
                        onSuccess(response);
                    } catch (error) {
                        onError(error);
                    }
                },
                prefill: {
                    name: 'Customer Name',
                    email: 'customer@example.com',
                    contact: '9999999999'
                },
                theme: {
                    color: '#3399cc'
                }
            };

            const razorpay = new window.Razorpay(options);
            razorpay.open();
        } catch (error) {
            onError(error);
        }
    };

    useEffect(() => {
        // Load Razorpay script
        const script = document.createElement('script');
        script.src = 'https://checkout.razorpay.com/v1/checkout.js';
        script.async = true;
        document.body.appendChild(script);

        return () => {
            document.body.removeChild(script);
        };
    }, []);

    return (
        <button 
            onClick={initializePayment}
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        >
            Pay Now
        </button>
    );
};

export default PaymentComponent; 