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

export const FormRegister = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [statusMessage, setStatusMessage] = useState<boolean>();
  const [errorMessage, setErrorMessage] = useState<string>();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      axios
        .post(`${process.env.NEXT_PUBLIC_URL_API}/auth/register`, {
          name,
          email,
          password,
          role: "ADMIN",
        })
        .then((res) => {
          if (res.status === 200) {
            setStatusMessage(true);
            setErrorMessage("Usuário criado com sucesso!");
            return;
          }
          setStatusMessage(false);
          setErrorMessage("Falha ao tentar criar usuário");
        });
    } catch (error) {}
  };

  return (
    <>
      <Card className="mx-auto max-w-sm mt-20">
        <CardHeader className="space-y-1">
          <CardTitle className="text-2xl font-bold">Register</CardTitle>
          <CardDescription>
            Enter your data for create a account
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={(e) => handleSubmit(e)}>
            <div className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email">Name</Label>
                <Input
                  id="name"
                  placeholder="Rodrigo"
                  required
                  autoComplete="name"
                  value={name}
                  type="text"
                  onChange={(e) => setName(e.target.value)}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="emailRegister">Email</Label>
                <Input
                  id="emailRegister"
                  placeholder="m@example.com"
                  required
                  autoComplete="email"
                  minLength={6}
                  maxLength={16}
                  value={email}
                  type="email"
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="passwordRegister">Password</Label>
                <Input
                  id="passwordRegister"
                  required
                  name="passwordRegister"
                  autoComplete="current-password"
                  minLength={6}
                  maxLength={16}
                  type="password"
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
                />
              </div>
              <Button className="w-full" type="submit">
                Create account
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
