import { FormLogin } from "@/components/auth/login";
import { FormRegister } from "@/components/auth/register";

export default function Home() {
  return (
    <>
      <FormLogin />
      <FormRegister />
    </>
  );
}
