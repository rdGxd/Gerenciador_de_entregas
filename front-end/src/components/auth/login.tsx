"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import axios from "axios";
import { FormEvent, useState } from "react";

export const FormLogin = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [statusMessage, setStatusMessage] = useState<boolean>();
  const [errorMessage, setErrorMessage] = useState<string>();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios
        .post(`${process.env.NEXT_PUBLIC_URL_API}/auth/login`, {
          email,
          password,
        })
        .then((res) => {
          if (res.data.token) {
            localStorage.setItem("token", res.data.token);
            window.location.href = "/product";
            setStatusMessage(true);
            setErrorMessage("Login realizado com sucesso.");
          }
        });
    } catch (error) {
      setStatusMessage(false);
      setErrorMessage("Email ou senha incorretos.");
      console.log(error);
    }
  };

  return (
    <>
      <Card className="mx-auto max-w-sm mt-20">
        <CardHeader className="space-y-1">
          <CardTitle className="text-2xl font-bold">Login</CardTitle>
          <CardDescription>
            Enter your email and password to login to your account
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={(e) => handleSubmit(e)}>
            <div className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  placeholder="m@example.com"
                  required
                  value={email}
                  minLength={6}
                  autoComplete="email"
                  maxLength={16}
                  type="email"
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="password">Password</Label>
                <Input
                  id="password"
                  required
                  type="password"
                  minLength={6}
                  autoComplete="current-password"
                  maxLength={16}
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
                />
              </div>
              <Button className="w-full" type="submit">
                Login
              </Button>

              <span
                className={"md:ml-60"}
                style={{
                  fontWeight: "bold",
                  color: statusMessage ? "green" : "red",
                  marginTop: "10px",
                }}
              >
                {errorMessage}
              </span>
            </div>
          </form>
        </CardContent>
      </Card>
    </>
  );
};
