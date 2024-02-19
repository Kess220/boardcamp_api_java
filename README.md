
# BoardCamp API Java

Uma api em java, destinada a uma locada de jogos


## Documentação da API

#### Criação do cliente

```http
  POST /customers
```
| Body        | Tipo       
| :---------- | :--------- 
| `name`      | `string` | 
| `cpf`      | `string` | 


#### Busca Cliente pelo ID

```http
  GET /customers/{id}
```
| Parâmetro       | Tipo       
| :---------- | :--------- 
| `id`      | `number` | 


#### Criação do Game

```http
  POST /games
```

| Body        | Tipo       
| :---------- | :--------- 
| `name`      | `string` | 
| `image`      | `string` | 
| `stockTotal`      | `number` | 
| `pricePerDay`      | `number` | 



#### Recebe todos os games

```http
  GET /games
```



#### Criação de um aluguel

```http
  POST /rentals
```

| Body        | Tipo       
| :---------- | :--------- 
| `customerId`      | `number` | 
| `gameId`      | `number` | 
| `daysRented`      | `number` | 


#### Retorno do Aluguel

```http
  PUT /rentals/{id}/return
```
| Parâmetro       | Tipo       
| :---------- | :--------- 
| `id`      | `number` | 

## Autor

- [@Kaio_Victor](https://www.github.com/Kess220)

