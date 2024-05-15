import { ModeToggle } from "./modeToggle";

export const FormLogin = () => {
  return (
    <>
      <form>
        <label htmlFor="email">Email</label>
        <input type="text" name="email" id="email" />
        <label htmlFor="password">Password</label>
        <input type="text" name="password" id="password" />
      </form>
    </>
  );
};
