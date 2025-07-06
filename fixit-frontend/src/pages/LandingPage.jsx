import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Button,
  Box,
  Grid,
  Paper,
  Card,
  CardContent,
  Accordion,
  AccordionSummary,
  AccordionDetails,
} from '@mui/material';
import {
  Security as SecurityIcon,
  AccessTime as AccessTimeIcon,
  AttachMoney as MoneyIcon,
  Favorite as FavoriteIcon,
  CheckCircle as CheckCircleIcon,
  Build as BuildIcon,
} from '@mui/icons-material';
import AOS from 'aos';
import 'aos/dist/aos.css';
import landingPageImg from '../assets/landing_page_img.jpg';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import linkedinIcon from '../assets/linkedin.png';
import facebookIcon from '../assets/facebook.png';
import instagramIcon from '../assets/instagram.png';
import githubIcon from '../assets/github-sign.png';

const features = [
  {
    icon: SecurityIcon,
    color: '#1976d2',
    title: 'Trusted Professionals',
    description: 'We vet all our service providers for quality and reliability, ensuring peace of mind.',
  },
  {
    icon: AccessTimeIcon,
    color: '#2e7d32',
    title: 'Convenience at Your Fingertips',
    description: 'Book services anytime, anywhere, directly from your device with our easy-to-use platform.',
  },
  {
    icon: MoneyIcon,
    color: '#ed6c02',
    title: 'Transparent Pricing',
    description: 'Get clear quotes upfront with no hidden fees. Know exactly what you\'ll pay before booking.',
  },
  {
    icon: FavoriteIcon,
    color: '#9c27b0',
    title: 'Support Local Businesses',
    description: 'Empower your community by hiring local talent and supporting neighborhood businesses.',
  },
  {
    icon: CheckCircleIcon,
    color: '#0288d1',
    title: 'Seamless Booking & Management',
    description: 'Easy-to-use tools for scheduling, communication, and payments make everything simple.',
  },
  {
    icon: BuildIcon,
    color: '#d32f2f',
    title: 'Wide Range of Services',
    description: 'From handyman tasks to deep cleaning, plumbing to electrical, we\'ve got you covered.',
  },
];

const services = [
  { title: 'Home Repairs', icon: '🏠' },
  { title: 'Cleaning Services', icon: '✨' },
  { title: 'Plumbing & Electrical', icon: '🔧' },
  { title: 'Appliance Repair', icon: '⚡' },
  { title: 'Painting & Decorating', icon: '🎨' },
  { title: 'Pest Control', icon: '🐜' },
];

const fancyHeading = {
  fontFamily: 'Pacifico, cursive',
  fontWeight: 700,
  letterSpacing: 2,
  color: '#fff',
  textShadow: '2px 2px 8px #00000033',
};

const fancySubHeading = {
  fontFamily: 'Montserrat, sans-serif',
  fontWeight: 700,
  color: '#fff',
  letterSpacing: 1,
};

const fancyCardHeading = {
  fontFamily: 'Pacifico, cursive',
  fontWeight: 700,
  color: '#222',
  letterSpacing: 1,
};

const fancyBody = {
  fontFamily: 'Montserrat, sans-serif',
  color: '#333',
};

const gradientBg = {
  background: 'linear-gradient(120deg, #6a11cb 0%, #2575fc 100%)',
};

const whyChooseFeatures = [
  { icon: SecurityIcon, color: '#1976d2', title: 'Secure Payments', description: 'Pay securely through our integrated Razorpay system. Your transactions are safe and encrypted.' },
  { icon: AccessTimeIcon, color: '#2e7d32', title: 'Fast Response', description: 'Get quick replies and service scheduling' },
  { icon: MoneyIcon, color: '#ed6c02', title: 'Fair Pricing', description: 'Transparent, competitive rates with no hidden fees.' },
  { icon: FavoriteIcon, color: '#9c27b0', title: 'Customer Support', description: 'Friendly help is always available, before and after your booking.' },
  { icon: CheckCircleIcon, color: '#0288d1', title: 'Satisfaction Guarantee', description: 'We stand by our service-if you are not happy, we will make it right.' },
  { icon: BuildIcon, color: '#d32f2f', title: 'All-in-One Platform', description: 'From plumbing to painting, find every home service in one place.' },
];

const faqs = [
  {
    question: 'How do I book a service on FixIt?',
    answer: 'Simply sign up, search for your service need, and book the one that fits you best—all in a few clicks.'
  },
  {
    question: 'Is my payment secure?',
    answer: 'Yes! All payments are processed securely through Razorpay, ensuring your transactions are safe and encrypted.'
  },
  {
    question: 'Can I become a service provider?',
    answer: 'Absolutely! Register as a provider, showcase your skills, and start receiving job requests from customers.'
  },
  {
    question: 'Are there any hidden fees?',
    answer: 'No, FixIt offers transparent pricing. You\'ll see all costs upfront before confirming your booking.'
  }
];

const LandingPage = () => {
  const navigate = useNavigate();
  const [expanded, setExpanded] = useState(false);
  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  useEffect(() => {
    AOS.init({ duration: 1000, once: true });
  }, []);

  return (
    <Box
      sx={{
        minHeight: '100vh',
        width: '100vw',
        position: 'relative',
        overflowX: 'hidden',
        backgroundImage: `url(${landingPageImg})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        backgroundAttachment: 'fixed',
      }}
    >
      {/* Global gradient overlay for readability */}
      <Box
        sx={{
          position: 'fixed',
          top: 0,
          left: 0,
          width: '100vw',
          height: '100vh',
          background: 'rgba(0,0,0,0.8)',
          zIndex: 0,
          pointerEvents: 'none',
        }}
      />
      <Box sx={{ position: 'relative', zIndex: 1 }}>
        {/* Hero Section */}
        <Box
          sx={{
            minHeight: '80vh',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            textAlign: 'center',
            px: 2,
            pb: 8,
            pt: { xs: 8, md: 12 },
            position: 'relative',
          }}
          data-aos="fade-down"
        >
          <Box sx={{ width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', pt: 4, pb: 2 }}>
            <Typography
              variant="h1"
              sx={{
                fontFamily: "'Dancing Script', cursive",
                fontWeight: 700,
                letterSpacing: 2,
                fontSize: { xs: '3rem', md: '5rem', lg: '6rem' },
                textShadow: '0 6px 32px rgba(0, 0, 0, 0.1), 0 2px 8px rgba(25, 118, 210, 0.3)',
                background: 'linear-gradient(90deg, #ADD8E6 0%, #87CEEB 100%)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                backgroundClip: 'text',
                color: 'transparent',
                mb: 1,
              }}
            >
              FixIt: Help at Your Doorstep
            </Typography>
          </Box>
          <Typography variant="h3" sx={{ fontFamily: "URW Chancery L, cursive	", fontWeight: 700, fontSize: { xs: '1.2rem', md: '2rem' }, mb: 2, color: '#fff', textShadow: '0 2px 8px #000a' }}>
            Find skilled plumbers, electricians and cleaners near you—book in under a minute.
          </Typography>
          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'center', mb: 4 }}>
            <Button
              variant="contained"
              size="large"
              onClick={() => navigate('/register')}
              sx={{
                fontFamily: 'Pacifico, cursive',
                fontWeight: 700,
                fontSize: '1.2rem',
                px: 4,
                py: 1.5,
                borderRadius: 3,
                background: 'linear-gradient(90deg, #1976d2 0%, #00c6fb 100%)',
                color: '#fff',
                boxShadow: '0 4px 24px 0 rgba(37, 117, 252, 0.15)',
                transition: 'transform 0.2s',
                '&:hover': {
                  transform: 'scale(1.07)',
                  background: 'linear-gradient(90deg, #00c6fb 0%, #1976d2 100%)',
                },
              }}
            >
              Get Started
            </Button>
            <Button
              variant="outlined"
              size="large"
              onClick={() => navigate('/login')}
              sx={{
                fontFamily: 'Pacifico, cursive',
                fontWeight: 700,
                fontSize: '1.2rem',
                px: 4,
                py: 1.5,
                borderRadius: 3,
                color: '#fff',
                border: '2px solid #fff',
                transition: 'transform 0.2s, border 0.2s',
                '&:hover': {
                  transform: 'scale(1.07)',
                  border: '2px solid #2575fc',
                  background: 'rgba(255,255,255,0.1)',
                },
              }}
            >
              Login
            </Button>
          </Box>
        </Box>

        {/* How It Works Section */}
        <Container maxWidth="lg" sx={{ py: { xs: 4, md: 6 }, px: { xs: 0, md: 2 } }}>
          <Typography
            component="h2"
            variant="h2"
            align="center"
            sx={{ ...fancyHeading, color: '#fff', mb: 8, fontSize: { xs: '2rem', md: '3rem' } }}
            data-aos="fade-up"
          >
            How It Works
          </Typography>
          <Box
            sx={{
              display: 'flex',
              flexDirection: { xs: 'column', md: 'row' },
              gap: { xs: 4, md: 4 },
              alignItems: 'stretch',
              justifyContent: 'center',
              width: '100%',
              mx: 'auto',
              maxWidth: 1200,
            }}
          >
            <Paper
              elevation={6}
              sx={{
                flex: 1,
                minWidth: 0,
                p: 5,
                borderRadius: 5,
                bgcolor: 'rgba(243,248,255,0.85)',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'flex-start',
                minHeight: 320,
              }}
            >
              <Typography variant="h4" sx={{ ...fancySubHeading, color: '#2575fc', mb: 3 }}>
                For Customers
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
                {[{
                  title: 'Search Your Need',
                  desc: 'Search for the service you require, from cleaning to repairs and more.'
                }, {
                  title: 'Compare & Choose',
                  desc: 'Review prices of local pros, and pick your favorite.'
                }, {
                  title: 'Book & Relax',
                  desc: 'Schedule your service and relax till the service is done!'
                }, {
                  title: 'Pay Securely',
                  desc: 'Make a secure payment by cash or online through Razorpay.'
                }].map((step, idx) => (
                  <Box key={idx} sx={{ display: 'flex', alignItems: 'flex-start', gap: 3 }}>
                    <Typography variant="h3" sx={{ color: '#2575fc', fontWeight: 900, fontFamily: 'Pacifico, cursive', minWidth: 40 }}>{idx + 1}</Typography>
                    <Box>
                      <Typography variant="h6" sx={{ fontWeight: 700, fontFamily: 'Pacifico, cursive', color: '#222' }}>{step.title}</Typography>
                      <Typography variant="body1" sx={{ ...fancyBody }}>{step.desc}</Typography>
                    </Box>
                  </Box>
                ))}
              </Box>
            </Paper>
            <Paper
              elevation={6}
              sx={{
                flex: 1,
                minWidth: 0,
                p: 5,
                borderRadius: 5,
                bgcolor: 'rgba(255,240,250,0.85)',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'flex-start',
                minHeight: 320,
              }}
            >
              <Typography variant="h4" sx={{ ...fancySubHeading, color: '#9c27b0', mb: 3 }}>
                For Service Providers
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 4 }}>
                {[{
                  title: 'Showcase Your Skills',
                  desc: 'Create a profile and add skills of your expertise and services.'
                }, {
                  title: 'Get Discovered',
                  desc: 'Let customers find you based on your skills and services.'
                }, {
                  title: 'Grow Your Business',
                  desc: 'Accept jobs, and expand your client base.'
                }, {
                  title: 'Get Paid Securely',
                  desc: 'Receive payments securely through Razorpay, with options for cash or online payment.'
                }].map((step, idx) => (
                  <Box key={idx} sx={{ display: 'flex', alignItems: 'flex-start', gap: 3 }}>
                    <Typography variant="h3" sx={{ color: '#9c27b0', fontWeight: 900, fontFamily: 'Pacifico, cursive', minWidth: 40 }}>{idx + 1}</Typography>
                    <Box>
                      <Typography variant="h6" sx={{ fontWeight: 700, fontFamily: 'Pacifico, cursive', color: '#222' }}>{step.title}</Typography>
                      <Typography variant="body1" sx={{ ...fancyBody }}>{step.desc}</Typography>
                    </Box>
                  </Box>
                ))}
              </Box>
            </Paper>
          </Box>
        </Container>

        {/* Why Choose FixIt Section */}
        <Box sx={{ bgcolor: 'transparent', py: 10 }}>
          <Container maxWidth="lg">
            <Typography
              component="h2"
              variant="h2"
              align="center"
              sx={{ ...fancyHeading, color: '#fff', mb: 8, fontSize: { xs: '2rem', md: '3rem' } }}
              data-aos="fade-up"
            >
              Why Choose FixIt
            </Typography>
            <Box sx={{ maxWidth: '1200px', mx: 'auto' }}>
              <Grid 
                container 
                spacing={4} 
                sx={{
                  display: 'grid',
                  gridTemplateColumns: {
                    xs: '1fr',
                    sm: '1fr',
                    md: 'repeat(3, 1fr)'
                  },
                  gap: 4,
                  width: '100%'
                }}
              >
                {whyChooseFeatures.map((feature, index) => {
                  const IconComponent = feature.icon;
                  return (
                    <Grid
                      item
                      key={index}
                      sx={{ 
                        display: 'flex',
                        justifyContent: 'center',
                        width: '100%'
                      }}
                    >
                      <Card
                        elevation={6}
                        sx={{
                          width: '100%',
                          height: 260,
                          display: 'flex',
                          flexDirection: 'column',
                          alignItems: 'center',
                          justifyContent: 'center',
                          borderRadius: 5,
                          p: 3,
                          bgcolor: 'rgba(255,255,255,0.85)',
                          boxShadow: '0 4px 24px 0 rgba(37,117,252,0.10)',
                          transition: 'transform 0.2s, box-shadow 0.2s',
                          '&:hover': {
                            transform: 'translateY(-8px) scale(1.04)',
                            boxShadow: `0 8px 32px 0 ${feature.color}33`,
                          },
                        }}
                      >
                        <Box sx={{ display: 'flex', justifyContent: 'center', mb: 2 }}>
                          <IconComponent sx={{ fontSize: 50, color: feature.color }} />
                        </Box>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="h3"
                          align="center"
                          sx={{ ...fancyCardHeading }}
                        >
                          {feature.title}
                        </Typography>
                        <Typography
                          variant="body2"
                          color="text.secondary"
                          align="center"
                          sx={{ fontFamily: 'Montserrat, sans-serif', color: '#444', fontWeight: 500 }}
                        >
                          {feature.description}
                        </Typography>
                      </Card>
                    </Grid>
                  );
                })}
              </Grid>
            </Box>
          </Container>
        </Box>

        {/* FAQs Section */}
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <Container maxWidth="md">
            <Box sx={{
              bgcolor: 'rgba(255,255,255,0.85)',
              borderRadius: 5,
              boxShadow: '0 4px 24px 0 rgba(37,117,252,0.10)',
              p: { xs: 2, md: 4 },
            }}>
              <Typography
                component="h2"
                variant="h3"
                align="center"
                sx={{ ...fancyHeading, color: '#1976d2', mb: 6, fontSize: { xs: '1.7rem', md: '2.5rem' } }}
                data-aos="fade-up"
              >
                Frequently Asked Questions
              </Typography>
              {/* FAQ Accordion */}
              {faqs.map((faq, idx) => (
                <Accordion
                  key={faq.question}
                  expanded={expanded === idx}
                  onChange={handleChange(idx)}
                  sx={{
                    mb: 2,
                    borderRadius: 2,
                    boxShadow: 'none',
                    '&:before': { display: 'none' },
                    bgcolor: 'rgba(255,255,255,0.95)'
                  }}
                >
                  <AccordionSummary
                    expandIcon={<ExpandMoreIcon sx={{ color: '#1976d2' }} />}
                    aria-controls={`faq-content-${idx}`}
                    id={`faq-header-${idx}`}
                    sx={{
                      fontWeight: 700,
                      color: '#1976d2',
                      fontSize: '1.1rem',
                      fontFamily: 'Montserrat, sans-serif',
                      borderRadius: 2,
                      minHeight: 56
                    }}
                  >
                    {faq.question}
                  </AccordionSummary>
                  <AccordionDetails sx={{ color: '#333', fontFamily: 'Montserrat, sans-serif', fontSize: '1rem' }}>
                    {faq.answer}
                  </AccordionDetails>
                </Accordion>
              ))}
            </Box>
          </Container>
        </Box>

        {/* Footer Section */}
        <Box sx={{ bgcolor: '#1A237E', color: '#fff', py: 6, mt: 8 }}>
          <Container maxWidth="lg" sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: 2 }}>
            <Typography variant="h5" sx={{ fontFamily: 'Playfair Display, serif', fontWeight: 600, mb: 1, textAlign: 'center', color: '#90caf9', fontSize : 40 }}>FixIt</Typography>
            <Typography variant="body2" sx={{ fontFamily: 'Montserrat, sans-serif', opacity: 0.85, textAlign: 'center', mb: 2 }}>
              Your trusted platform for local services. Connecting you with skilled professionals, fast and easy.
            </Typography>
            <Box sx={{ display: 'flex', gap: 3, mb: 2, justifyContent: 'center' }}>
              <a href="https://www.linkedin.com/in/ayush-soni-87371a261/" target="_blank" rel="noopener noreferrer">
                <img src={linkedinIcon} alt="LinkedIn" style={{ width: 40, height: 40, objectFit: 'contain', display: 'block' }} />
              </a>
              <a href="https://www.facebook.com/" target="_blank" rel="noopener noreferrer">
                <img src={facebookIcon} alt="Facebook" style={{ width: 40, height: 40, objectFit: 'contain', display: 'block' }} />
              </a>
              <a href="https://www.instagram.com/" target="_blank" rel="noopener noreferrer">
                <img src={instagramIcon} alt="Instagram" style={{ width: 40, height: 40, objectFit: 'contain', display: 'block' }} />
              </a>
              <a href="https://github.com/Ayush301077" target="_blank" rel="noopener noreferrer">
                <img src={githubIcon} alt="GitHub" style={{ width: 40, height: 40, objectFit: 'contain', display: 'block' }} />
              </a>
            </Box>
            <Typography variant="body2" sx={{ fontFamily: 'Montserrat, sans-serif', opacity: 0.7, textAlign: 'center' }}>
              &copy; {new Date().getFullYear()} FixIt. All rights reserved.
            </Typography>
          </Container>
        </Box>
      </Box>
    </Box>
  );
};

export default LandingPage; 